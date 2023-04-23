import { createBrowserRouter } from "react-router-dom";
import MovieList from "../components/movie/MovieList";
import PeopleList from "../components/people/PeopleList";
import Settings from "../components/settings/Settings";
import Movie from "../components/movie/Movie";
import Person from "../components/people/Person";

export const MOVIE_LIST_PATH = "/"
export const PEOPLE_LIST_PATH = "/people"
export const SETTINGS_PATH = "/settings"
export const MOVIE_PATH = "/movies/:id"
export const PERSON_PATH = "/people/:id"

export const router = createBrowserRouter([
    {
        path: MOVIE_LIST_PATH,
        element: <MovieList/>,
    },
    {
        path: PEOPLE_LIST_PATH,
        element: <PeopleList/>
    },
    {
        path: SETTINGS_PATH,
        element: <Settings/>
    },
    {
        path: MOVIE_PATH,
        element: <Movie/>
    },
    {
        path: PERSON_PATH,
        element: <Person/>
    }
]);