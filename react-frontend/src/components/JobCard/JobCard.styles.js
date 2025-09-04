// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    card: {
        marginBottom: 10,
        marginHorizontal: 10,
        paddingVertical: 16,
        paddingHorizontal: 24,
        borderRadius: 10,
        backgroundColor: "#F9FAFB",
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 5,
        elevation: 4,
    },
    title: {
        marginBottom: 4,
        fontSize: 18,
        fontWeight: "bold",
        color: "#111827",
    },
    company: {
        marginBottom: 2,
        fontSize: 14,
        color: "#6B7280",
    },
    meta: {
        marginBottom: 6,
        fontSize: 13,
        color: "#9CA3AF",
    },
    salary: {
        marginBottom: 6,
        fontSize: 14,
        fontWeight: "600",
        color: "#6366F1",
    },
    description: {
        fontSize: 14,
        color: "#111827",
    },
});


export default styles;