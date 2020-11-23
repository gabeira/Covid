# Covid
[![Build Status](https://app.bitrise.io/app/e84b9d61dc3e37af/status.svg?token=uadA4D9ZvYTeRezGFrGqRw&branch=master)](https://app.bitrise.io/app/e84b9d61dc3e37af)

This is an Android App to show COVID19 information.

## Configuration

This Project was developed using [Android Studio](https://developer.android.com/studio/)

To Download the code from this Repository you can use Android Studio or command line, running:
```sh
git clone https://github.com/gabeira/Covid
```
To Build the Project, you can use Android Studio or from command line, just run:
```sh
./gradlew build
```
To install debug app from command line, use:
```sh
./adb install /app/build/outputs/apk/app-debug.apk
```

## External Libs Reference

- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Glide Image](https://github.com/bumptech/glide)
- [OkHttp](https://square.github.io/okhttp/)

## Tests

Tests can be started using Android Studio or from the command line,
to run the Unit Tests just use:
```sh
./gradlew test
```

Connected Android Tests requires to have a device or emulator connected:
```sh
./gradlew connectedAndroidTest
```