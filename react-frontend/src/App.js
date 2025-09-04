// Internal Contexts
import AuthProvider from "@/contexts/Auth/AuthProvider";

// Internal Navigation
import AppNavigator from "@/navigation/Navigators/AppNavigator";


const App = () => {
    return (
        <AuthProvider>
            <AppNavigator />
        </AuthProvider>
    );
};


export default App;