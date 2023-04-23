import {Movie, useGetMoviesQuery} from "../../store/api/movies";
import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {styled} from "@stitches/react";
import MovieItem from "./MovieListItem";

const Container = styled("div", {
    display: "flex",
    flexDirection: "column",
    paddingLeft: "8px",
    paddingRight: "8px"
})

const MovieList = () => {
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const { data, error, isLoading } = useGetMoviesQuery({movieDatabase: selectedDatabase})
    console.log(data)

    const renderMovies = () => {
        if(isLoading){
            return <h1>Loading</h1>
        }
        else {
            return data?.data.movies.map(movie => <MovieItem movie={movie}/>)
        }
    }
    return (
        <Container>
            {renderMovies()}
        </Container>
    )
}

export default MovieList