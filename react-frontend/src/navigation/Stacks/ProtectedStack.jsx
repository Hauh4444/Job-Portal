// External Libraries
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// Internal Screens
import TabNavigator from "@/navigation/Tabs/TabNavigator";
import JobScreen from "@/screens/JobScreen/JobScreen";
import ApplyScreen from "@/screens/ApplyScreen/ApplyScreen";
import ViewFileScreen from "@/screens/ViewFileScreen/ViewFileScreen";
import EditContactScreen from "@/screens/EditContactScreen/EditContactScreen";


const Stack = createNativeStackNavigator();


const ProtectedStack = () => (
    <Stack.Navigator id="protected-stack">
        <Stack.Screen name="Main" component={ TabNavigator } />
        <Stack.Screen name="Job" component={ JobScreen } />
        <Stack.Screen name="Apply" component={ ApplyScreen } />
        <Stack.Screen name="View File" component={ ViewFileScreen } />
        <Stack.Screen name="Edit Contact" component={ EditContactScreen } />
    </Stack.Navigator>
);


export default ProtectedStack;
