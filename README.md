# MoviesApp

Purpose
-----------
- This app has been creaeted in purpose to make users easier to search and save films.

Functionality
--------------
in this app you can:
- see endles list of popular movies.
- search movies.
- see movie details.
- see trailers and related videos of each given film.
- see simmilar movies of each given film.
- save movies to your list and view them offline.

How To Use
-----------
- to use app user needs an account.

<img src="https://user-images.githubusercontent.com/66998427/187107806-1a4c2f5e-6957-4896-ba46-51f27f6532c7.png" width="200" height="300">

![login](https://user-images.githubusercontent.com/66998427/187107806-1a4c2f5e-6957-4896-ba46-51f27f6532c7.png)
![register](https://user-images.githubusercontent.com/66998427/187107816-e3e0e87a-1867-40e6-b1cb-b9c57d9f617e.png)

on the first page (Home) you can see:
- Now straming section (if you want to see more just click on it).
- list of Popular Movies.
- you can scroll through the genres to see them all.

![home](https://user-images.githubusercontent.com/66998427/187107865-c65d13f1-8db7-411a-a55e-012a3b31bf9d.png)
![nowStreaming](https://user-images.githubusercontent.com/66998427/187107867-3e2ac33a-ac7b-452d-8775-71e981f16c3e.png)

to see more details about current film, just click on it and you will navigate to movie details screen.
- here you can see poster, description, raiting, genres and more.
- you can click on "save film" icon to add the film to your list.
- or click again to remove it from your list.

![removed](https://user-images.githubusercontent.com/66998427/187107905-3b362bd5-508b-443e-9018-d37457b55ad1.png)
![saved](https://user-images.githubusercontent.com/66998427/187107910-827182a6-c78b-44c7-81af-562b3ab4d155.png)

below you can seeL list of trailers and related movies from youtube.

https://user-images.githubusercontent.com/66998427/187108024-ebc84b54-0c5f-4402-a8ce-5fa99d86d2d1.mp4

if you swipe to the right, you will see simmilar movies to a given one.

![similar movies](https://user-images.githubusercontent.com/66998427/187108082-d9ce3c02-743a-46b0-946b-48ca21be3a4b.png)

in the search section, type and search movies.

![search](https://user-images.githubusercontent.com/66998427/187108107-338eb139-390c-41b4-be42-d28b930fca6c.png)

in the profile section you can see:
- user information.
- Log out button.
- list of your saved movies.

![profile](https://user-images.githubusercontent.com/66998427/187108144-d0f786d9-199d-42f5-94e7-a70c3f44cfce.png)

if you open application without internet, or you lost it while using the app,
certainly you can't use app the same proper way, but you can still see your list of saved movies.

https://user-images.githubusercontent.com/66998427/187108314-1a2782cd-3f12-4364-9119-efac740c8d4e.mp4

Used
--------
- MVVM architecture 
- dependency injection with Koin
- retrofit
- Gson converterFactory
- room
- coroutines
- paging
- androidYoutubePlayer
- viewPager
- splash screen
- firebaseAuth
- firebaseDatabase
- navigation components
- glide

Project Structure
------------------
Project contains 5 main package and an application class:

1) data :
  - models
  - pagingSource
  - providers (logal and global)
  - repository
  
2) di :
  - localStorageModule
  - serviceModule
  - repositoryModule
  - useCasesModule
  - viewModelsModule
  
3) domein: 
- interceptor
- ConnectionLiveData
- useCases:(
    checkIfMovieExist,
    deleteMovie,
    getGenres,
    getMovies,
    getMovieTrailers,
    getNowStreamingMovies,
    getPopularMovies,
    getSimilarMovies,
    saveMovie,
    searchMovies)

4) other: 
- adapters
- base (BaseFragment, BaseUseCase)
- common(Constants, Extensions, typealias)
- ResponseHelpers

5) ui: 
- loggedIn(
home(movieDetails [simmilarMovies, trailers] seeMoreNowStreaming),
profile,
search,
viewPagerContainerFragment)
- login,
- register





