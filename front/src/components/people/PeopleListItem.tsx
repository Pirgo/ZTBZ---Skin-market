import {Movie} from "../../store/api/movies";
import {styled} from "@stitches/react";
import { useNavigate } from "react-router-dom";
import {Human} from "../../store/api/people";

const Container = styled("div", {
    display: "flex",
    cursor: "pointer"
})

const CoverImage = styled("img", {
    height: "200px"
})

const InnerContainer = styled("div", {

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
                <p>{person.firstName} {person.secondName}</p>
            </InnerContainer>
        </Container>
    )
}

export default PeopleListItem