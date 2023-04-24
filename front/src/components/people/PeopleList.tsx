import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {styled} from "@stitches/react";
import {useGetPeopleQuery} from "../../store/api/people";
import PeopleListItem from "./PeopleListItem";

const Container = styled("div", {
    display: "flex",
    flexDirection: "column",
    paddingLeft: "8px",
    paddingRight: "8px"
})

const PeopleList = () => {
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const { data, error, isLoading } = useGetPeopleQuery({movieDatabase: selectedDatabase})
    console.log(data)

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
            {renderPeople()}
        </Container>
    )
}

export default PeopleList