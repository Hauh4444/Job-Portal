// External Libraries
import { StyleSheet } from "react-native";


const styles = StyleSheet.create({
    bar: {
        backgroundColor: "#FFFFFF",
        height: 60,
        paddingBottom: 0,
        shadowColor: "#111827",
        shadowOffset: { width: 0, height: -2 },
        shadowOpacity: 0.1,
        shadowRadius: 5,
        elevation: 4,
    },
    label: {
        fontSize: 10,
        fontWeight: "600",
    },
    icon: {
        marginTop: 2,
    },
    activeTintColor: "#FFFFFF",
    activeBackgroundColor: "#6366F1",
    inactiveTintColor: "#6B7280",
});


export default styles;