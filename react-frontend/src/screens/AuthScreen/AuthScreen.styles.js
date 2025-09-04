// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#FFFFFF",
    },
    card: {
        width: "100%",
        maxWidth: 320,
        padding: 24,
        borderColor: "#E5E7EB",
        borderWidth: 1,
        borderRadius: 16,
        backgroundColor: "#F9FAFB",
    },
    title: {
        marginBottom: 24,
        textAlign: "center",
        color: "#0F0F0F",
        fontSize: 24,
        fontWeight: "600",
        letterSpacing: 1.2,
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
        width: "100%",
        height: 48,
        marginTop: 8,
        justifyContent: "center",
        alignItems: "center",
        borderRadius: 8,
    },
    buttonText: {
        color: "#F9FAFB",
        fontSize: 16,
        fontWeight: "500",
    },
});


export default styles;