// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#F9FAFB",
    },
    section: {
        width: "100%",
        paddingVertical: 20,
        paddingHorizontal: 15,
        borderColor: "#E5E7EB",
        borderWidth: 1,
    },
    finalSection: {
        width: "100%",
        height: 86,
        paddingVertical: 20,
        paddingHorizontal: 15,
        borderColor: "#E5E7EB",
        borderWidth: 1,
    },
    footer: {
        width: "100%",
        paddingVertical: 20,
        paddingHorizontal: 15,
        position: "absolute",
        bottom: 0,
    },
    title: {
        fontSize: 30,
        fontWeight: "600",
        color: "#111827",
    },
    head: {
        paddingBottom: 10,
        fontSize: 22,
        fontWeight: "600",
        color: "#111827",
    },
    text: {
        fontSize: 16,
        color: "#111827",
    },
    card: {
        marginVertical: 5,
        padding: 10,
        alignSelf: "flex-start",
        borderRadius: 8,
        backgroundColor: "#F1F5F9",
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 3,
        elevation: 4,
    },
    cardText: {
        fontWeight: "normal",
        fontSize: 16,
        color: "#111827",
    },
    appliedCard: {
        marginTop: 10,
        paddingVertical: 5,
        paddingHorizontal: 10,
        alignSelf: "flex-start",
        backgroundColor: "#6366F1",
        borderColor: "#6366F1",
        borderWidth: 1,
        borderRadius: 8,
    },
    appliedCardText: {
        fontWeight: "normal",
        fontSize: 16,
        color: "#FFFFFF",
    },
    row: {
        paddingVertical: 5,
        flexDirection: "row",
        alignItems: "center",
    },
    strongText: {
        fontSize: 16,
        fontWeight: "600",
        color: "#111827",
    },
    description: {
        marginVertical: 10,
        fontWeight: "normal",
        fontSize: 16,
        color: "#6B7280",
    },
    link: {
        textDecorationLine: "underline",
        color: "#0000FF",
    },
    dot: {
        fontSize: 16,
        marginHorizontal: 4,
        color: "#111827",
    },
    icon: {
        marginRight: 4,
        fontSize: 16,
        color: "#6B7280",
    },
    button: {
        width: "90%",
        marginHorizontal: "auto",
        padding: 10,
        borderRadius: 10,
    },
    buttonText: {
        textAlign: "center",
        fontSize: 20,
        fontWeight: "600",
        color: "#FFFFFF",
    },
});


export default styles;