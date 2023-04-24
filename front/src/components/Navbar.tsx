import {styled} from "@stitches/react";
import {MOVIE_LIST_PATH, PEOPLE_LIST_PATH, SETTINGS_PATH} from "../router/Routes";

const Container = styled("div", {
    width: "100%",
    height: "50px",
    display: "flex",
    gap: "8px",
    backgroundColor: "#0B2447",
    alignItems: "center"
})

const ContainerItem = styled("div", {
    color: "#FFEBEB",
    fontSize: "24px",
})

const SettingsItem = styled(ContainerItem, {
    marginLeft: "auto"
})

const Navbar = () => {
    return (
        <Container>
            <ContainerItem>
                <a href={MOVIE_LIST_PATH}>Movies</a>
            </ContainerItem>
            <ContainerItem>
                <a href={PEOPLE_LIST_PATH}>People</a>
            </ContainerItem>
            <SettingsItem>
                <a href={SETTINGS_PATH}>Settings</a>
            </SettingsItem>
        </Container>
    )
}

export default Navbar