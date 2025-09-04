// External Libraries
import { createNativeStackNavigator } from "@react-navigation/native-stack";

// Internal Screens
import AuthScreen from "@/screens/AuthScreen/AuthScreen";


const Stack = createNativeStackNavigator();


const PublicStack = () => (
    <Stack.Navigator id="public-stack" initialRouteName="Auth">
        <Stack.Screen name="Auth" component={ AuthScreen } />
    </Stack.Navigator>
);


export default PublicStack;
