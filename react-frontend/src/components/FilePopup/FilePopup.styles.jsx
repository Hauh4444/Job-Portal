// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    page: {
        width: "100%",
        height: "100%",
        padding: 10,
        position: "absolute",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "rgba(0,0,0,0.25)",
    },
    card: {
        width: "100%",
        padding: 12,
        borderRadius: 10,
        backgroundColor: "#F9FAFB",
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 5,
        elevation: 4,
    },
    icon: {
        marginLeft: "auto",
        fontSize: 32,
        color: "#6B7280",
    },
    content: {
        padding: 15,
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
    },
    text: {
        fontSize: 20,
        fontWeight: 600,
        color: "#111827",
    },
    button: {
        paddingVertical: 5,
        paddingHorizontal: 12,
        borderRadius: 8,
        backgroundColor: "#F1F5F9",
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 3,
        elevation: 4,
    },
    buttonText: {
        fontSize: 20,
        color: "#111827",
    },
});


export default styles;