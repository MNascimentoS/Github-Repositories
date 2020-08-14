fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android test
```
fastlane android test
```
Runs all the tests
### android bundle_release
```
fastlane android bundle_release
```
Build App
### android build
```
fastlane android build
```
Build App
### android appcenter
```
fastlane android appcenter
```
Deploy a new version to the AppCenter
### android appcenter_build
```
fastlane android appcenter_build
```
Build and Deploy a new version to the AppCenter
### android google_play
```
fastlane android google_play
```
Deploy a new bundle version to the Google Play in any track
### android google_play_apk
```
fastlane android google_play_apk
```
Deploy a new apk version to the Google Play in any track
### android post_to_slack
```
fastlane android post_to_slack
```


----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
