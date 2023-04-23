import { createBrowserRouter } from "react-router-dom";
import MovieList from "../components/movie/MovieList";
import PeopleList from "../components/people/PeopleList";
import Settings from "../components/settings/Settings";

export const MOVIE_LIST_PATH = "/"
export const PEOPLE_LIST_PATH = "/people"
export const SETTINGS_PATH = "/settings"

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
    }
]);