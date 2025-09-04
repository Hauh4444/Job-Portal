// External Libraries
import { useState } from "react";
import { View, Text, TextInput, Pressable, Alert } from "react-native";
import { LinearGradient } from "expo-linear-gradient";

// Internal Utilities
import axiosInstance from "@/utils/axiosInstance";

// Styles
import styles from "./EditContactScreen.styles";


const EditContactScreen = ({ navigation, route }) => {
    const [profile, setProfile] = useState(route.params.profile);


    const handleChange = (field, value) => {
        setProfile(prev => ({ ...prev, [field]: value }));
    };


    const handleSave = async () => {
        try {
            await axiosInstance.post("/profile", profile);
            Alert.alert("Success", "Contact Info Updated Successfully");
        } catch (error) {
            console.error("Contact info update failed", error.response?.data || error.message);
            Alert.alert("Contact Info Update Failed", error.response?.data?.message || "An error occurred");
        } finally {
            navigation.goBack();
        }
    };


    return (
        <View style={ styles.container }>
            <View style={ styles.section }>
                <Text style={ styles.title }>Contact Information</Text>
            </View>

            <View style={ styles.section }>
                <Text style={ styles.label }>Name</Text>
                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("name", value) }
                    value={ profile.name }
                    placeholder="Name"
                    placeholderTextColor="#6B7280"
                />

                <Text style={ styles.label }>Phone</Text>
                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("phone", value) }
                    value={ profile.phone }
                    placeholder="Phone"
                    placeholderTextColor="#6B7280"
                />

                <Text style={ styles.label }>Email</Text>
                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("email", value) }
                    value={ profile.email }
                    placeholder="Email"
                    placeholderTextColor="#6B7280"
                />
            </View>

            <View style={ styles.section }>
                <Text style={ styles.label }>Country</Text>
                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("country", value) }
                    value={ profile.country }
                    placeholder="Country"
                    placeholderTextColor="#6B7280"
                />

                <Text style={ styles.label }>Location</Text>
                <TextInput
                    style={ styles.input }
                    onChangeText={ (value) => handleChange("location", value) }
                    value={ profile.location }
                    placeholder="Location"
                    placeholderTextColor="#6B7280"
                />
            </View>

            <View style={ styles.section }>
                <Pressable onPress={ () => handleSave() }>
                    <LinearGradient
                        style={ styles.button }
                        colors={ ["#7C3AED", "#6366F1"] }
                        start={{ x: 0, y: 0 }}
                        end={{ x: 1, y: 0 }}
                    >
                        <Text style={ styles.buttonText }>SAVE</Text>
                    </LinearGradient>
                </Pressable>
            </View>
        </View>
    );
};


export default EditContactScreen;