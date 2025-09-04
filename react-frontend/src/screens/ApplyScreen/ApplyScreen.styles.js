// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#FFFFFF",
    },
    title: {
        fontSize: 24,
        fontWeight: "600",
        color: "#111827",
    },
    section: {
        width: "100%",
        paddingVertical: 20,
        paddingHorizontal: 15,
        borderColor: "#E5E7EB",
        borderWidth: 1,
    },
    sectionHead: {
        marginBottom: 20,
        marginHorizontal: "auto",
        fontSize: 20,
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
    fileCard: {
        marginVertical: 5,
        paddingVertical: 20,
        paddingHorizontal: 12,
        borderRadius: 10,
        backgroundColor: "#F9FAFB",
        flexDirection: "row",
        alignItems: "center",
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 5,
        elevation: 4,
    },
    fileIcon: {
        marginRight: 12,
        fontSize: 40,
        color: "#6366F1",
    },
    fileText: {
        fontSize: 16,
        color: "#111827",
    },
    fileButton: {
        marginLeft: "auto",
    },
    ellipsis: {
        fontSize: 30,
        color: "#6366F1",
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