import { useSelector, useDispatch } from 'react-redux'
import { setDataBase } from "../../store/slice/settings";
import {RootState} from "../../store/store";
const Settings = () => {
    const dispatch = useDispatch()
    const selectedDataBase = useSelector((state: RootState) => state.dataBase)

    const isSelected = (value: string) => {
        return value === selectedDataBase
    }

    return (
        <div>
            <input type="radio" value="POSTGRESQL" name="db" checked={isSelected("POSTGRESQL")} onChange={() => dispatch(setDataBase("POSTGRESQL"))}/> Postgres
            <input type="radio" value="MONGO_DB" name="db" checked={isSelected("MONGO_DB")} onChange={() => dispatch(setDataBase("MONGO_DB"))}/> Mongo
            <input type="radio" value="INFLUX" name="db" checked={isSelected("INFLUX")} onChange={() => dispatch(setDataBase("INFLUX"))}/> Influx
            <input type="radio" value="FAKE_DATABASE" name="db" checked={isSelected("FAKE_DATABASE")} onChange={() => dispatch(setDataBase("FAKE_DATABASE"))}/> Fake db
        </div>
    )
}

export default Settings