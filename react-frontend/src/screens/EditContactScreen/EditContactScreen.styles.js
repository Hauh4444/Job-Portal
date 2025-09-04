// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#FFFFFF",
    },
    section: {
        width: "100%",
        paddingVertical: 20,
        paddingHorizontal: 15,
        borderColor: "#E5E7EB",
        borderWidth: 1,
    },
    title: {
        fontSize: 30,
        fontWeight: "600",
        color: "#111827",
    },
    label: {
        marginBottom: 10,
        fontSize: 16,
        fontWeight: "600",
        color: "#111827",
    },
    input: {
        width: "100%",
        height: 48,
        marginBottom: 16,
        paddingHorizontal: 12,
        borderColor: "#D1D5DB",
        borderWidth: 1,
        borderRadius: 8,
        backgroundColor: "#FFFFFF",
        color: "#111827",
        fontSize: 16,
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