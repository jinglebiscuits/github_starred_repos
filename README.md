# github_starred_repos
A simple Android app to display the most starred GitHub repositories.

The project will default to loading the top 5 repositories. If you provide an OAuth2 token, the app will load 100 repositories.

You can provide the token by adding it to your gradle.properties file.

`GITHUB_TOKEN=[your_token]`

## Testing the app
You can build the apk by using gradle `./gradlew assembleDebug`. The apk will be created in `.../app/build/outputs/apk/debug/app-debug.apk`

You can then install on your device with `adb install [path_to_apk]`