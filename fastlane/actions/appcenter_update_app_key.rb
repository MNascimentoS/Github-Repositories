module Fastlane
  module Actions

    Encoding.default_external = Encoding::UTF_8
    Encoding.default_internal = Encoding::UTF_8

    require "json"
    require "fileutils"

    class AppcenterUpdateAppKeyAction < Action
      def self.description
        "Action to call application details to update app secret"
      end

      def self.details
        "This action will call AppCenter API to recover app key to update this data in app \n\nCalling this method alows to after the app is upload in AppCenter, will alert the tester for furthers updates."
      end

      def self.is_supported?(platform)
        platform == :android
      end

      def self.available_options
        # Define all options your action supports.

        # Below a few examples
        [
          FastlaneCore::ConfigItem.new(key: :api_token,
                                       env_name: "FL_APPCENTER_UPDATE_APP_KEY_API_TOKEN", # The name of the environment variable
                                       description: "API Token for AppcenterUpdateAppKeyAction", # a short description of this parameter
                                       verify_block: proc do |value|
                                          UI.user_error!("No API token for AppcenterUpdateAppKeyAction given, pass using `api_token: 'token'`") unless (value and not value.empty?)
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
          FastlaneCore::ConfigItem.new(key: :development,
                                       env_name: "FL_APPCENTER_UPDATE_APP_KEY_DEVELOPMENT",
                                       description: "Create a development certificate instead of a distribution one",
                                       is_string: false, # true: verifies the input is a string, false: every kind of value
                                       default_value: false) # the default value if the user didn't provide one
        ]
      end

      def self.run(params)
        connection = self.connection
        url = "v0.1/apps/#{params[:owner_name]}/#{params[:app_name]}"

        response = connection.get(url) do |req|
          req.headers['X-API-Token'] = params[:api_token]
          req.headers['internal-request-source'] = "fastlane"
        end

        UI.message("DEBUG: #{response.status} #{JSON.pretty_generate(response.body)}
") if ENV['DEBUG']

        case response.status
          when 200...300
            self.update_app_key(response.body["app_secret"])   
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

      def self.update_app_key(app_key)
        UI.message("DEBUG: App Secret #{app_key}") if ENV['DEBUG']

        keys_xml = File.join("app", "src", "main", "res", "values", "keys.xml")
        File.write(keys_xml, File.read(keys_xml).sub(/<string name=\"appcenter_key\">.*<\/string>/, "<string name=\"appcenter_key\">#{app_key}</string>"))

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
