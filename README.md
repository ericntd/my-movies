# The app
- Let you search movies on [TheMovieDb](https://www.themoviedb.org) by keyword and list the results

# The code
- Model-View-Presenter (MVP) architecture with Espresso tests
- Demonstration of an API-back app (not a single Helloworld text view one) which is simple yet relatable enough
- The use of [TheMovieDb](https://www.themoviedb.org)'s API, please sign up to get your own access token

## The automated tests
- Espresso - TODO: to mock Retrofit reponse
- Non-UI/ logic unit tests - to be written

## What are included?
- Dagger - to provide a Retrofit service with local caching for the app
  - Without it, it's difficult to pass a context object to the OkHttpClient powering Retrofit
  - Without it, it'd more difficult to swap the real service with a "mock" one later on in a non-UI/ logic unit test (most likely with Robolectric) later on

## What are not included?
- RxJava - for simplicity and focus on the MVP demonstration, will create a separate project with RxJava

# Insipration
https://github.com/ivacf/archi

# References
https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2

