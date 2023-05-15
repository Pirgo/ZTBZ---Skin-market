import {styled} from "@stitches/react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {setPeopleSearchValue} from "../../store/slice/searchFilters";

const Container = styled("div", {

})


const PeopleSearchBox = () => {
    const dispatch = useDispatch()
    const searchVal = useSelector((state: RootState) => state.searchFilters.people.searchValue)

    const handleInput = (searchValue: string) => {
        dispatch(setPeopleSearchValue(searchValue))
    }

    return (
        <Container>
            <input
                onChange={(e) => handleInput(e.target.value)}
                value={searchVal}
                type="text"
                name="people-search"
                id="people-search"
                placeholder="Name"
            />
        </Container>
    )
}

export default PeopleSearchBox