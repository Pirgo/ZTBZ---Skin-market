import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {styled} from "@stitches/react";
import {useGetPeopleQuery} from "../../store/api/people";
import PeopleListItem from "./PeopleListItem";
import PeopleSearchBox from "../search/PeopleSearchBox";
import {useState} from "react";
import {MyPaginate} from "../movie/MovieList";

const Container = styled("div", {
    display: "flex",
    flexDirection: "column",
    paddingLeft: "8px",
    paddingRight: "8px"
})

const InnerContainer = styled("div", {
    marginTop: "16px",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    gap: "16px"
})

const SearchContainer = styled("div", {
    display: "flex",
    flexDirection: "row",
    gap: "8px",
    marginTop: "8px",
    justifyContent: "center",
    alignItems: "center"
})

const PEOPLE_PER_PAGE = 10;

const PeopleList = () => {
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)

    const searchText = useSelector((state: RootState) => state.searchFilters.people.searchValue)
    const [localSearchText, setLocalSearchText] = useState(searchText)
    const [offset, setOffset] = useState(0)

    const { data, error, isLoading } = useGetPeopleQuery({
        movieDatabase: selectedDatabase,
        searchText: localSearchText,
        offset,
        pageSize: PEOPLE_PER_PAGE
    })

    const handlePageClick = (e: any) => {
        const newOffset = e.selected + 1
        setOffset(newOffset)
    }

    const search = () => {
        setLocalSearchText(searchText)
    }

    const renderPeople = () => {
        if(isLoading){
            return <h1>Loading</h1>
        }
        else {
            return data?.data.people.map(person => <PeopleListItem person={person}/>)
        }
    }
    return (
        <Container>
            <SearchContainer>
                <PeopleSearchBox/>
                <button onClick={search}>Search</button>
            </SearchContainer>
            <InnerContainer>
                {renderPeople()}
            </InnerContainer>
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

export default PeopleList