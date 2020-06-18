# sbp-pluggable-project

This is a PoC to demonstrate the feasibility of loading an external jar to extend the behaviour of a webapp, through the [sbp library](https://github.com/hank-cp).

### Intro

There's a parent maven project and 2 maven sub-modules:
* **sbp-main-app** is the main app that must receive the plugin. It exposes the `GreetingsController` that defines an `extension points` in order to aggregate greetings provided by plugins.
* **plugins/sbp-sample-plugin** provides two types of greetings: italian greetings and french greetings.

### Quick Start
1. Run the spring boot application **sbp-main-app**
1. Query the `GreetingsController`: GET `http://localhost:8080/greetings`
1. **expectations**: you should receive as response an array with two greetings: italian and french

This is the `development mode`, because the plugin is not built and the main app discoveres it probing the `plugins` folder.
