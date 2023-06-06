import {useSelector} from "react-redux";
import {RootState} from "../../store/store";
import {useNavigate, useParams} from "react-router-dom";
import {useGetPersonQuery} from "../../store/api/people";
import {styled} from "@stitches/react";


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

const Person = () => {
    const navigate = useNavigate()
    const selectedDatabase = useSelector((state: RootState) => state.settings.dataBase)
    const { id } = useParams()
    //TODO possible error, but should not happen
    const {data, error, isLoading} = useGetPersonQuery({movieDatabase: selectedDatabase, id: id || ""})

    const renderDeathInfo = () => {
        if(data !== undefined && data.data.deathDay !== null) {
            return <p>Death: {data.data.deathDay.join("/")}</p>
        }
    }

    const renderActorInfo = () => {
        if(data !== undefined && data.data.functions.actor !== null) {
            return (
                <>
                    <span>Played in:</span>
                    {data.data.functions.actor.map(p =>
                        <CustomRef onClick={() => navigate(`/movies/${p.filmId}`)}>
                            {p.title}
                        </CustomRef>).slice(0, 4)
                    }
                </>
            )
        }
    }

    const renderDirectorInfo = () => {
        if(data !== undefined && data.data.functions.director !== null) {
            return (
                <>
                    <span>Directed:</span>
                    {data.data.functions.director.map(p =>
                        <CustomRef onClick={() => navigate(`/movies/${p.filmId}`)}>
                            {p.title}
                        </CustomRef>).slice(0, 4)
                    }
                </>
            )
        }
    }

    return (
        <Container>
            <InnerContainer>
                <h1>{data?.data.firstName} {data?.data.secondName}</h1>
                <p>Birthday: {data?.data.birthday.join("/")}</p>
                <p>Place of birth: {data?.data.placeOfBirth}</p>
                {renderDeathInfo()}
                <p>Description: {data?.data.description}</p>
                {renderActorInfo()}
                <br/>
                {renderDirectorInfo()}
            </InnerContainer>
            <CoverImage src={data?.data.photoUrl}/>
        </Container>
    )
}

export default Person
