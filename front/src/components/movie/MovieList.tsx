import {useGetMoviesQuery} from "../../store/api/movies";
import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {styled} from "@stitches/react";
import MovieItem from "./MovieListItem";
import MovieSearchBox from "../search/MovieSearchBox";
import YearFilter from "../search/YearFilter";
import PlatformFilter from "../search/PlatformFilter";
import {useState} from "react";
import ReactPaginate from "react-paginate";

const Container = styled("div", {
    display: "flex",
    flexDirection: "column",
    paddingLeft: "8px",
    paddingRight: "8px"
})

export const MyPaginate = styled(ReactPaginate, {
    // You can redefine classes here, if you want.
    activeClassName: 'active',
    marginBottom: "2rem",
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    listStyleType: "none",

    "& li a": {
        borderRadius: "7px",
        border: "gray 1px solid",
        cursor: "pointer"
    },

    "& li.previous a": {
        borderColor: "transparent"
    },

    "& li.next a": {
        borderColor: "transparent"
    },

    "& li.break a": {
        borderColor: "transparent"
    },

    "& li.active a": {
        backgroundColor: "#0366d6",
        borderColor: "transparent",
        color: "white",
        minWidth: "32px",
    },

    "& li.disabled a": {
        color: "grey",
    },

    "& li.disable a": {
        cursor: "default",
    },

    "& li.disable": {
        cursor: "default",
    },
})


const MOVIES_PER_PAGE = 10;

const MovieList = () => {
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const searchText = useSelector((state: RootState) => state.searchFilters.movies.searchValue)
    const year = useSelector((state: RootState) => state.searchFilters.movies.year)
    const platform = useSelector((state: RootState) => state.searchFilters.movies.platform)
    const [localSearchText, setLocalSearchText] = useState(searchText)
    const [localYear, setLocalYear] = useState(year)
    const [localPlatform, setLocalPlatform] = useState(platform)
    const [offset, setOffset] = useState(0)

    const { data, error, isLoading } = useGetMoviesQuery(
        {
            movieDatabase: selectedDatabase,
            year: localYear,
            searchText: localSearchText,
            platform: localPlatform,
            offset,
            pageSize: MOVIES_PER_PAGE
        }
    )

    const handlePageClick = (e: any) => {
        const newOffset = e.selected + 1
        setOffset(newOffset)
    }

    const search = () => {
        setLocalSearchText(searchText)
        setLocalYear(year)
        setLocalPlatform(platform)
    }

    const renderMovies = () => {
        if(isLoading){
            return <h1>Loading</h1>
        }
        else {
            return data?.data.movies.map(movie => <MovieItem movie={movie} key={movie.id}/>)
        }
    }
    return (
        <Container>
            <MovieSearchBox/>
            <YearFilter/>
            <PlatformFilter/>
            <button onClick={search}>Search</button>
            {renderMovies()}
            <MyPaginate
                breakLabel="..."
                nextLabel="next >"
                onPageChange={handlePageClick}
                pageRangeDisplayed={5}
                pageCount={(data?.data.totalPages ?? 1) - 1}
                previousLabel="< previous"
                renderOnZeroPageCount={null}
                activeClassName="active"
            />
        </Container>
    )
}

export default MovieList