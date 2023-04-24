import {Movie} from "../../store/api/movies";
import {styled} from "@stitches/react";
import { useNavigate } from "react-router-dom";

const Container = styled("div", {
    display: "flex",
    cursor: "pointer"
})

const CoverImage = styled("img", {
    height: "200px"
})

const InnerContainer = styled("div", {

})

type MovieItemProps = {
    movie: Movie
}

const MovieItem = ({movie}: MovieItemProps) => {
    const navigate = useNavigate()
    return (
        <Container onClick={() => navigate(`/movies/${movie.id}`)}>
            <CoverImage src={movie.coverUrl}/>
            <InnerContainer>
                <p>{movie.title}</p>
            </InnerContainer>
        </Container>
    )
}

export default MovieItem