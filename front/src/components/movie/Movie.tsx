import {useParams} from "react-router-dom";
import {useGetMovieQuery} from "../../store/api/movies";
import {useSelector} from "react-redux";
import {RootState} from "../../store/store";

const Movie = () => {
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const { id } = useParams()
    //TODO possible error, but should not happen
    const {data, error, isLoading} = useGetMovieQuery({movieDatabase: selectedDatabase, id: id || ""})
    return (
        <>
            <p>{data?.data.title} </p>
            <p>{data?.data.plot}</p>
            <p>Budget: {data?.data.budget}</p>
        </>
    )
}

export default Movie