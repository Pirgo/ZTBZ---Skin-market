import {styled} from "@stitches/react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {setYear} from "../../store/slice/searchFilters";
import { ChangeEvent } from "react";

const Container = styled("div", {})


const YearFilter = () => {
    const dispatch = useDispatch()
    const year = useSelector((state: RootState) => state.searchFilters.movies.year)

    const handleInput = (e: ChangeEvent<HTMLInputElement>) => {
        const year = parseInt(e.target.value)
        if (isNaN(year)){
            dispatch(setYear(undefined))
            return
        }
        dispatch(setYear(year))
    }

    return (
        <Container>
            <input
                onChange={(e) => handleInput(e)}
                value={year}
                type="number"
                name="movie-year"
                id="movie-year"
                placeholder="Year"
            />
        </Container>
    )
}

export default YearFilter