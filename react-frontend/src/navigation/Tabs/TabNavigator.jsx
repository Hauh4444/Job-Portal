// External Libraries
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

// External Icons
import Ionicons from "react-native-vector-icons/Ionicons";

// Internal Navigations
import { tabs } from "./TabsConfig";

// Styles
import styles from "./TabNavigator.styles";


const Tab = createBottomTabNavigator();


const TabNavigator = () => (
    <Tab.Navigator
        id="tab-stack"
        screenOptions={({ route }) => {
            const tab = tabs.find((t) => t.name === route.name);

            return {
                headerShown: false,
                tabBarActiveTintColor: styles.activeTintColor,
                tabBarActiveBackgroundColor: styles.activeBackgroundColor,
                tabBarInactiveTintColor: styles.inactiveTintColor,
                tabBarLabelStyle: styles.label,
                tabBarStyle: styles.bar,
                tabBarIconStyle: styles.icon,
                tabBarIcon: ({ focused, color }) => (
                    <Ionicons
                        name={ focused ? tab.icon.focused : tab.icon.unfocused }
                        size={ 22 }
                        color={ color }
                    />
                ),
            };
        }}
    >
        { tabs.map(({ name, component }) => (
            <Tab.Screen key={ name } name={ name } component={ component } />
        )) }
    </Tab.Navigator>
);

export default TabNavigator;
