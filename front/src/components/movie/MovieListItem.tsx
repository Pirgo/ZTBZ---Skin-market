import {Movie} from "../../store/api/movies";
import {styled} from "@stitches/react";
import { useNavigate } from "react-router-dom";

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
    marginLeft: "8px"
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
                <h1>{movie.title}</h1>
                <p>Year: {movie.productionYear}</p>
                <p>Length: {movie.length} min.</p>
                {movie.genres.length > 0 ? movie.genres.map(g => g.name + ", ").slice(0, 2) + "..." : null}
            </InnerContainer>
        </Container>
    )
}

export default MovieItem