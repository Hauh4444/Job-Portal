// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#FFFFFF",
    },
    searchBarContainer: {
        width: "100%",
        paddingHorizontal: 8,
        backgroundColor: "#F9FAFB",
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 5,
        elevation: 4,
    },
    searchBarInputContainer: {
        backgroundColor: "#F9FAFB",
        borderRadius: 8,
    },
    searchBarInput: {
        color: "#111827",
    },
    categoryScroll: {
        paddingVertical: 8,
        paddingLeft: 10,
    },
});


export default styles;