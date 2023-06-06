import {styled} from "@stitches/react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {setPlatform} from "../../store/slice/searchFilters";
import { ChangeEvent } from "react";

const Container = styled("div", {})

const platforms = [
    {value: undefined, label: 'All', key: 0},
    {value: 'Netflix', label: 'Netflix', key: 1},
    {value: 'Hulu', label: 'Hulu', key: 2},
    {value: 'Amazon Prime', label: 'Amazon Prime', key: 3},
]

const PlatformFilter = () => {
    const dispatch = useDispatch()
    const platform = useSelector((state: RootState) => state.searchFilters.movies.platform)

    const handleChange = (e: ChangeEvent<HTMLSelectElement>) => {
        if(e.target.value === "All"){
            dispatch(setPlatform(undefined))
            return
        }
        dispatch(setPlatform(e.target.value))
    }

    const options = platforms.map(elem => (
        <option value={elem.value} key={elem.key}>{elem.label}</option>
    ));

    return (
        <Container>
            <label>Platform </label>
            <select value={platform} onChange={handleChange}>
                {options}
            </select>
        </Container>
    )
}

export default PlatformFilter