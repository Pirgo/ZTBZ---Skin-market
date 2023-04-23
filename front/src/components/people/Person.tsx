import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {useParams} from "react-router-dom";
import {useGetPersonQuery} from "../../store/api/people";

const Person = () => {
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const { id } = useParams()
    //TODO possible error, but should not happen
    const {data, error, isLoading} = useGetPersonQuery({movieDatabase: selectedDatabase, id: parseInt(id || "")})
    return (
        <>
            <p>{data?.data.firstName} </p>
            <p>{data?.data.secondName}</p>
            <p>Birthday: {data?.data.birthday}</p>
        </>
    )
}

export default Person
