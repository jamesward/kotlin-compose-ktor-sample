# Kotlin Compose Ktor Sample

*NOTE: You must use JDK 11 or higher*

Run the client:

1. [Download Android Command Line Tools](https://developer.android.com/studio)

1. Install the SDK:

    ```sh
    mkdir android-sdk
    cd android-sdk
    unzip PATH_TO_SDK_ZIP/sdk-tools-linux-VERSION.zip
    mv cmdline-tools latest
    mkdir cmdline-tools
    mv latest cmdline-tools
    cmdline-tools/latest/bin/sdkmanager --update
    cmdline-tools/latest/bin/sdkmanager "platforms;android-32" "build-tools;32.0.0" "extras;google;m2repository" "extras;android;m2repository"
    cmdline-tools/latest/bin/sdkmanager --licenses
    ```

1. Set an env var pointing to the `android-sdk`

    ```sh
    export ANDROID_SDK_ROOT=PATH_TO_SDK/android-sdk
    echo "sdk.dir=$(realpath $ANDROID_SDK_ROOT)" > local.properties
    ```

1. Run the build from this project's dir:

    ```sh
    ./gradlew build
    ```

1. Run the app:
    * From the command line:

        ```sh
        ./gradlew installDebug
        ```

    * From Android Studio / IntelliJ, navigate to
        `src/main/kotlin` and right-click on
        `MainActivity` and select `Run`.
