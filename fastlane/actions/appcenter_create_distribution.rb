module Fastlane
  module Actions
    class AppcenterCreateDistributionAction < Action
      def self.description
        "Action to create distribution groups to a app in AppCenter"
      end

      def self.details
        "This action allows to create a distribution group to an app of a organization in AppCenter and share with a list of emails."
      end

      def self.run(params)
        UI.message("Creating #{params[:distribution_name]} distribution group for #{params[:app_name]} app")
        connection = self.connection
        url = "v0.1/apps/#{params[:owner_name]}/#{params[:app_name]}/distribution_groups"

        body ||= {
          "name": params[:distribution_name],
          "display_name": params[:distribution_display_name] || params[:distribution_name]
        }

        UI.message("DEBUG: POST #{url}") if ENV['DEBUG']
        UI.message("DEBUG: POST body: #{JSON.pretty_generate(body)}\n") if ENV['DEBUG']

        response = connection.post(url) do |req|
          req.headers['X-API-Token'] = params[:api_token]
          req.headers['internal-request-source'] = "fastlane"
          req.headers['accept'] = "application/json"
          req.body = body
        end

        UI.message("DEBUG: #{response.status} #{JSON.pretty_generate(response.body)}\n") if ENV['DEBUG']

      case response.status
        when 200...300
          self.add_testers_email(params[:api_token], params[:owner_name], params[:app_name], params[:distribution_name], params[:emails])
        when 409
          UI.error("Error, a org with the same name already exists for this app")
          self.add_testers_email(params[:api_token], params[:owner_name], params[:app_name], params[:distribution_name], params[:emails])
        when 401
          UI.user_error!("Auth Error, provided invalid token")
          false
        when 404
          UI.error("Not found, invalid owner or application name")
          false
        when 500...600
          UI.abort_with_message!("Internal Service Error, please try again later")
        else
          UI.error("Error #{response.status}: #{response.body}")
          false
        end
      end

      def self.add_testers_email(api_token, owner_name, app_name, distribution_group_name, emails)
        UI.message("Sharing #{distribution_group_name} with users")

        connection = self.connection

        url = "v0.1/apps/#{owner_name}/#{app_name}/distribution_groups/#{distribution_group_name}/members"

        email_list = emails.split(",")

        body ||= {
          "user_emails": email_list
        }

        response = connection.post(url) do |req|
          req.headers['X-API-Token'] = api_token
          req.headers['internal-request-source'] = "fastlane"
          req.headers['accept'] = "application/json"
          req.body = body
        end

        UI.message("DEBUG: #{response.status} #{JSON.pretty_generate(response.body)}\n") if ENV['DEBUG']

        case response.status
        when 200...300
          response.body
        when 409
          response.body
        when 401
          UI.user_error!("Auth Error, provided invalid token")
          false
        when 404
          UI.error("Not found, invalid owner or application name")
          false
        when 500...600
          UI.abort_with_message!("Internal Service Error, please try again later")
        else
          UI.error("Error #{response.status}: #{response.body}")
          false
        end
      end

      def self.available_options
        # Define all options your action supports. 
        
        # Below a few examples
        [
          FastlaneCore::ConfigItem.new(key: :api_token,
                                       env_name: "FL_APPCENTER_CREATE_DISTRIBUTION_API_TOKEN", # The name of the environment variable
                                       description: "API Token for AppcenterCreateDistributionAction", # a short description of this parameter
                                       verify_block: proc do |value|
                                          UI.user_error!("No API token for AppcenterCreateDistributionAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
                                       end),
          FastlaneCore::ConfigItem.new(key: :owner_name,
                                       env_name: "FL_APPCENTER_CREATE_DISTRIBUTION_OWNER_NAME", # The name of the environment variable
                                       description: "Owner Name for AppcenterCreateDistributionAction", # a short description of this parameter
                                       verify_block: proc do |value|
                                          UI.user_error!("No Owner name for AppcenterCreateDistributionAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
                                       end),
          FastlaneCore::ConfigItem.new(key: :app_name,
                                       env_name: "FL_APPCENTER_CREATE_DISTRIBUTION_APP_NAME", # The name of the environment variable
                                       description: "App Name for AppcenterCreateDistributionAction", # a short description of this parameter
                                       verify_block: proc do |value|
                                          UI.user_error!("No App name for AppcenterCreateDistributionAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
                                       end),         
          FastlaneCore::ConfigItem.new(key: :distribution_name,
                                       env_name: "FL_APPCENTER_CREATE_DISTRIBUTION_DISTRIBUTION_NAME", # The name of the environment variable
                                       description: "Distribution Name for AppcenterCreateDistributionAction", # a short description of this parameter
                                       verify_block: proc do |value|
                                          UI.user_error!("No App name for AppcenterCreateDistributionAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
                                       end),         
          FastlaneCore::ConfigItem.new(key: :distribution_display_name,
                                       env_name: "FL_APPCENTER_CREATE_DISTRIBUTION_DISTRIBUTION_DISPLAY_NAME", # The name of the environment variable
                                       description: "Distribution Display Name for AppcenterCreateDistributionAction", # a short description of this parameter
                                       is_string: true,
                                       default_value: ""),  
          FastlaneCore::ConfigItem.new(key: :emails,
                                       env_name: "FL_APPCENTER_CREATE_DISTRIBUTION_EMAILS", # The name of the environment variable
                                       description: "Emails for AppcenterCreateDistributionAction", # a short description of this parameter
                                       verify_block: proc do |value|
                                          UI.user_error!("No Emails for AppcenterCreateDistributionAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
                                       end),                              
          FastlaneCore::ConfigItem.new(key: :development,
                                       env_name: "FL_CREATE_APPCENTER_DISTRIBUTION_DEVELOPMENT",
                                       description: "Create a development certificate instead of a distribution one",
                                       is_string: false, # true: verifies the input is a string, false: every kind of value
                                       default_value: false) # the default value if the user didn't provide one
        ]
      end

      def self.is_supported?(platform)
        true
      end

      # create request
      def self.connection(upload_url = nil, dsym = false, csv = false)
        require 'faraday'
        require 'faraday_middleware'

        default_api_url = "https://api.appcenter.ms"
        if ENV['APPCENTER_ENV']&.upcase == 'INT'
          default_api_url = "https://api-gateway-core-integration.dev.avalanch.es"
        end

        options = {
          url: upload_url || default_api_url
        }

        UI.message("DEBUG: BASE URL #{options[:url]}") if ENV['DEBUG']

        Faraday.new(options) do |builder|
          if upload_url
            builder.request :multipart unless dsym
            builder.request :url_encoded unless dsym
          else
            builder.request :json
          end
          builder.response :json, content_type: /\bjson$/ unless csv
          builder.use FaradayMiddleware::FollowRedirects
          builder.adapter :net_http
        end
      end
    end
  end
end
