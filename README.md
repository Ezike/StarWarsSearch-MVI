![Android Build](https://github.com/Ezike/StarWarsSearch/workflows/Android%20Build/badge.svg)

# Star wars search

Search for star wars characters from [Star Wars API](https://swapi.dev/).
Built on Clean Architecture with MVI (Uni-directional data flow)
                                                                                                                        
## Features
* Clean Architecture with MVI (Uni-directional data flow)
* Kotlin Coroutines with Flow
* Dagger Hilt
* Kotlin Gradle DSL
* GitHub actions for CI

Hi 👋🏼👋🏼👋🏼, thanks for checking out/reviewing my project. For the rest of this document, I will be explaining the reasons for the technical decisions I made for this case study, the problems I faced, and what I learnt from them.

## Table of Contents

- [Prerequisite](#prerequisite)
- [Design](#Design)
- [Architecture](#architecture)
- [Testing](#testing)
- [Tech stack](#Techstack)
- [Extras](#Extras)

## Prerequisite
To build this project, you require:
- Android Studio 4.2 canary 4 or higher
- Gradle 6.5 

<h2 align="left">Screenshots</h2>
<h4 align="center">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_recents.jpg" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_search.png" width="30%" vspace="10" hspace="10">
<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596748100/star_wars_detail.jpg" width="30%" vspace="10" hspace="10"><br>                                                                                                                
                                                                                                                                  
## Design
Before stepping into any coding and architecture decisions, I first had to come up with a sort of idea of how I wanted the app to look, and the kind of experience I wanted users to have when using the app. This also guided my decisions on what architecture and tools were best suited to bring this user experience to life. In addition to having a search screen and a detail screen, I also added a search history screen where users can revisit any previously searched item without needing to type text into the search bar and wait for results.
As seen in the images of the app above [Design](#Design), the app launches into the recent searches screen where the user can either see their recent searches or a prompt that tells them their recent searches will be displayed there later. If the user has recent searches, there's also a button that allows the user clear them.

Typing text into the search bar transitions the user into a `searching state` which could lead to loaded search results, empty or error state.

One thing to take into account is how much state handling needs to be done in order to deliver a stellar user experience. This is why I chose to use the `Model-View-Intent` architecture for the presentation layer of the app. This will be discussed in-depth in the section below.

## Architecture

The application follows clean architecture mainly because of the many benefits it brings to software which includes scalability, maintainability and testability.
It also enforces separation of concerns and dependency inversion where higher and lower level layers all depend on abstractions. In the project, I separate my layers into different gradle modules namely:

- Domain
- Data 
- Remote 
- Cache

These modules/layers are Java/Kotlin modules except cache. The catch here is that we want independence from the Android framework. One of the key points of clean architecture is that your low level layers should be platform agnostic. We can plug our Domain, data and presentation layers into a kotlin multiplatform project and it will run just fine because we don't depend on the android framework. The cache and remote layers are implementation details that can be provided in any form (Firebase, GraphQl server, REST, ROOM, SQLDelight, etc) as long as it conforms to the business rules / contracts defined in the data layer which in turn also conforms to contract defined in domain.
The project also has one feature module `character_search` that holds the UI code and presents data to the users. The main app module does nothing more than just tying all the many layers of our app together. 

#### Presentation
As stated earlier, the presentation layer is implemented with MVI architecture. There is a kotlin module called presentation which defines the contract of what our apps presentation should look like. The layer is also platform agnostic, allowing us to switch implementation details like whether to use ViewModel or not at will.

<img src="https://res.cloudinary.com/diixxqjcx/image/upload/v1596780277/mvi_image.png" width="20%" vspace="8" hspace="8"><br>   
                                                                                                                                  
MVI architecture has two main components - The model and the view, everything else is the data that flows between these two components. The view state comes from the Model and goes into the View for rendering. Intents come from the View and is consumed by the model for processing. This relationship is most times refered to as unidirectional Data Flow.

I have a model class called `State machine` which encapsulates logic of how to process intents and make new view states. It relies on an intent processor that takes intents from the view, liases with a third-party (in this case our domain layer) to process the intent and then returns a result. Our results are taken up by a view state reducer function that uses our previous state and the result from the intent processor to make a new state that will be rendered on the UI. 
The views (fragments/components) output intents and take state as input. The viewmodel which is our presenter outputs state and takes in intents to process. 

The viewmodel in our architecture is very lean, depending solely on the state machine. Since it survives configuration changes, it ensures our user data persists across screen rotation.  
MVI is a great architecture when you don't want any surprises in your user experience as state only comes from one source and is immutable. On the other hand it does come with a lot of boilerplate. Thankfully, there are a couple of libraries out there that abstract the implementation details and make it a lot easier to use. I took more of vanilla approach in order to represent the core concepts of MVI.

#### State rendering
For each screen, there is a sealed class of Viewstate, Viewintent and results. It's also possible to want to render multiple view states in one screen. and this led to creation of `view components`. 
View components are basically compound UI components that extend a viewGroup, which knows how to render it's own view state and also emit intents. In the search screen we have two components - `SearchHistoryView` and `SearchResultView`. The `SearchFragment` then passes state to these components to render on the screen. It also takes intents from the components to process.

The detail screen was a bit more complex. I created a component to render each detail - `PlanetView`, `FilmView`, `SpeciesView`, and `ProfileView`. These encapsulate view logic for rendering success, error and empty states for the corresponding detail. The data for the views are fetch concurrently which allows either of them to render whenever its data is loading. It also allows the user to retry the data fetch for each individual component if it fails. The states for each of them is decoupled from one another and is cached in a Flow persisted in the Fragment's viewModel.

#### Domain

#### Data

## Testing

## Tech stack

Libraries used in the whole application are:
- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
  and act as a channel between use cases and ui
- [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.  
- [Moshi](https://github.com/square/moshi) - JSON Parser,used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
- [Truth](https://truth.dev/) - Assertions Library,provides readability as far as assertions are concerned
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses on the star wars api with the retrofit client.
- [Robolectric](http://robolectric.org/) - Unit test on android framework.
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [Dagger Hilt](https://dagger.dev/hilt)
- [Kotlin Gradle DSL](https://guides.gradle.org/migrating-build-logic-from-groovy-to-kotlin)
