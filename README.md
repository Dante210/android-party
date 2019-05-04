# Task for a Great Android developer

If you found this task it means we are looking for you!

> Note: To clone this repository you will need [GIT-LFS](https://git-lfs.github.com/)

## Few simple steps

1. Fork this repo
2. Do your best
3. Prepare pull request and let us know that you are done

## Few simple requirements

- Send authorization request (POST) to http://playground.tesonet.lt/v1/tokens to generate token with body: `{"username": "tesonet", "password": "partyanimal"}`. (Don't forget Content-Type)
- Get servers list from http://playground.tesonet.lt/v1/servers. Add header to request: `Authorization: Bearer <token>`
- Design should be recreated as closely as possible
- Bonus: implement smooth animated transition from login through loader to server list screen
- Bonus: implement persistent storage of the downloaded server data
- Bonus: have a good set of unit tests

*Note:* The bonus requirements are optional. While they are nice to have, it's much more important to have the basics nailed.

## Approach to the task ##
# Main points when designing the solution for this task were simplicity, scalability and maintainability.
Project design based on [Model View View-Model](https://codingwithmitch.com/blog/getting-started-with-mvvm-android/) 

Tools used in the project:
1. [Android Jetpack](https://developer.android.com/jetpack)
2. [Koin](https://insert-koin.io/)
3. [Retrofit](https://square.github.io/retrofit/)
4. [RxKotlin](https://github.com/ReactiveX/RxKotlin)