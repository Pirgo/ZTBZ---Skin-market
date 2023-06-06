import {useParams} from "react-router-dom";
import {useGetMovieQuery} from "../../store/api/movies";
import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {styled} from "@stitches/react";
import { useNavigate } from "react-router-dom";


const Container = styled("div", {
    width: "750px",
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    borderRadius: "8px",
    border: "1px solid black",
    margin: "auto",
    marginTop: "16px"
})

const InnerContainer = styled("div", {
    marginLeft: "8px",
})

const CoverImage = styled("img", {

})

const CustomRef = styled("li", {
    cursor: "pointer",
})
const Movie = () => {
    const navigate = useNavigate()
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const { id } = useParams()
    //TODO possible error, but should not happen
    const {data, error, isLoading} = useGetMovieQuery({movieDatabase: selectedDatabase, id: id || ""})

    const renderPlatforms = () => {
        if(data !== undefined && data.data.platforms.length > 0){
            return (
                <>
                    <span> Available on: </span>
                    {data.data.platforms.map(p => <li>{p.name}</li>)}
                </>
            )
        }
    }

    const renderGenres = () => {
        if(data !== undefined && data.data.genres.length > 0){
            return (
                <>
                    <span>Genres:</span>
                    {data.data.genres.map(p => <li>{p.name}</li>).slice(0, 4)}
                </>
            )
        }
    }

    const renderActors = () => {
        if(data !== undefined && data.data.actors.length > 0){
            return (
                <>
                    <span>Actors:</span>
                    {data.data.actors.map(p =>
                        <CustomRef onClick={() => navigate(`/people/${p.id}`)}>
                            {p.name}
                        </CustomRef>).slice(0, 4)
                    }
                </>
            )
        }
    }

    const renderDirectors = () => {
        if(data !== undefined && data.data.directors.length > 0){
            return (
                <>
                    <span>Directors:</span>
                    {data.data.directors.map(p =>
                        <CustomRef onClick={() => navigate(`/people/${p.id}`)}>
                            {p.name}
                        </CustomRef>).slice(0, 2)
                    }
                </>
            )
        }
    }

    return (
        <Container>
            <InnerContainer>
                <h1>{data?.data.title} </h1>
                <p>Plot: {data?.data.plot}</p>
                <p>Budget: {data?.data.budget}$</p>
                <p>Production year: {data?.data.productionYear}</p>
                <p>Length: {data?.data.length}min.</p>
                <p>Rating: {data?.data.rating.toFixed(2)}</p>
                {renderPlatforms()}
                <br/>
                {renderGenres()}
                <br/>
                {renderActors()}
                <br/>
                {renderDirectors()}
            </InnerContainer>
            <CoverImage src={data?.data.coverUrl}/>
        </Container>
    )
}

export default Movie