import {styled} from "@stitches/react";
import {MOVIE_LIST_PATH, PEOPLE_LIST_PATH, SETTINGS_PATH} from "../router/Routes";

const Container = styled("div", {
    width: "100%",
    height: "50px",
    display: "flex",
    backgroundColor: "black",
    alignItems: "center",
    paddingLeft: "8px",
    paddingRight: "8px",
    gap: "8px"
})

const CutomATag = styled("a", {
    fontSize: "24px",
    color: "white",
})

const Navbar = () => {
    return (
        <Container>
                <CutomATag href={MOVIE_LIST_PATH}>Movies</CutomATag>
                <CutomATag href={PEOPLE_LIST_PATH}>People</CutomATag>
                <CutomATag href={SETTINGS_PATH}>Settings</CutomATag>
        </Container>
    )
}

export default Navbar