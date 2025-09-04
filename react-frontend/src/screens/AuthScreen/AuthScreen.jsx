// External Libraries
import { useState } from "react";
import { View, Text, TextInput, Pressable } from "react-native";
import { LinearGradient } from "expo-linear-gradient";
import SHA256 from "crypto-js/sha256";

// Internal Contexts
import { useAuth } from "@/contexts/Auth/AuthContext";

// Styles
import styles from "./AuthScreen.styles";


const AuthScreen = ({ navigation }) => {
    const { user, error, login } = useAuth();

    const [info, setInfo] = useState({ username: "", password: "" });


    const handleChange = (field, value) => {
        setInfo(prev => ({ ...prev, [field]: value }));
    };


    const handleSubmit = async () => {
        await login({
            username: info.username,
            hashedPassword: SHA256(info.password).toString(),
        });
    };


    return (
        <View style={ styles.container }>
            <View style={ styles.card }>
                <Text style={ styles.title }>
                    Welcome to Indigrow!
                </Text>

                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("username", value) }
                    value={ info.username }
                    placeholder="Username"
                    placeholderTextColor="#6B7280"
                />
                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("password", value) }
                    value={ info.password }
                    placeholder="Password"
                    placeholderTextColor="#6B7280"
                    secureTextEntry={ true }
                />

                <Pressable onPress={ () => handleSubmit() }>
                    <LinearGradient
                        style={ styles.button }
                        colors={ ["#7C3AED", "#6366F1"] }
                        start={{ x: 0, y: 0 }}
                        end={{ x: 1, y: 0 }}
                    >
                        <Text style={ styles.buttonText }>SIGN IN</Text>
                    </LinearGradient>
                </Pressable>
            </View>
        </View>
    );
};


export default AuthScreen;