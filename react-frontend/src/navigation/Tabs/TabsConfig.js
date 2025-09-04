// Internal Screens
import HomeScreen from "@/screens/HomeScreen/HomeScreen";
import ProfileScreen from "@/screens/ProfileScreen/ProfileScreen";


export const tabs = [
    {
        name: "Home",
        component: HomeScreen,
        icon: {
            focused: "home",
            unfocused: "home-outline",
        },
    },
    {
        name: "Profile",
        component: ProfileScreen,
        icon: {
            focused: "person",
            unfocused: "person-outline",
        },
    },
];
