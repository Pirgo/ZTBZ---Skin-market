import {styled} from "@stitches/react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {setMovieSearchValue} from "../../store/slice/searchFilters";

const Container = styled("div", {

})


const MovieSearchBox = () => {
    const dispatch = useDispatch()
    const searchVal = useSelector((state: RootState) => state.searchFilters.movies.searchValue)

    const handleInput = (searchValue: string) => {
        dispatch(setMovieSearchValue(searchValue))
    }

    return (
        <Container>
            <input
                onChange={(e) => handleInput(e.target.value)}
                value={searchVal}
                type="text"
                name="movie-search"
                id="movie-search"
                placeholder="Title"
            />
        </Container>
    )
}

export default MovieSearchBox