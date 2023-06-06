import {Movie} from "../../store/api/movies";
import {styled} from "@stitches/react";
import { useNavigate } from "react-router-dom";
import {Human} from "../../store/api/people";

const Container = styled("div", {
    display: "flex",
    cursor: "pointer",
    width: "50%",
    border: "1px solid black",
    borderRadius: "8px",
    overflow: "hidden"
})

const CoverImage = styled("img", {
    height: "200px"
})

const InnerContainer = styled("div", {
    marginLeft: "8px",
    display: "flex",
    alignItems: "center"
})

type PeopleListItemProps = {
    person: Human
}

const PeopleListItem = ({person}: PeopleListItemProps) => {
    const navigate = useNavigate()
    return (
        <Container onClick={() => navigate(`/people/${person.id}`)}>
            <CoverImage src={person.photoUrl}/>
            <InnerContainer>
                <h1>{person.firstName} {person.secondName}</h1>
            </InnerContainer>
        </Container>
    )
}

export default PeopleListItem