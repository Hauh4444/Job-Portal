// External Libraries
import { useCallback, useState } from "react";
import { Alert, Pressable, Text, TextInput, View } from "react-native";
import { useFocusEffect } from "@react-navigation/native";
import { LinearGradient } from "expo-linear-gradient";

// External Icons
import Ionicons from "react-native-vector-icons/Ionicons";

// Internal Components
import FilePopup from "@/components/FilePopup/FilePopup";

// Internal Utilities
import axiosInstance from "@/utils/axiosInstance";

// Styles
import styles from "./ApplyScreen.styles";
import SHA256 from "crypto-js/sha256";


const ApplyScreen = ({ navigation, route }) => {
    const [isFilePopupOpen, setIsFilePopupOpen] = useState(false);
    const [profile, setProfile] = useState({});
    const [applicationForm, setApplicationForm] = useState({
        name: "",
        email: "",
        phone: "",
        linkedin: "",
        website: "",
    });
    const job = route.params.job;


    const fetchData = async () => {
        const response = await axiosInstance.get("/profile");
        setProfile(response.data);
        setApplicationForm(prev => ({
            ...prev,
            name: response.data.name,
            email: response.data.email,
            phone: response.data.phone,
        }));
    }


    useFocusEffect(
        useCallback(() => {
            fetchData().catch((err) => console.error(err));
        }, [])
    );


    const handleChange = (field, value) => {
        setProfile(prev => ({ ...prev, [field]: value }));
    };


    const handleViewFile = () => {
        navigation.navigate("View File", { file: profile.resume });
    };


    const handleEditFile = () => {
        setIsFilePopupOpen(true);
    };


    const handleFileUpload = async (selectedFile) => {
        const formData = new FormData();
        formData.append("file", {
            uri: selectedFile.uri,
            name: selectedFile.name,
            type: selectedFile.mimeType || "application/octet-stream",
        });

        try {
            await axiosInstance.post("/profile/upload", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });
            Alert.alert("Success", "File Uploaded Successfully");
        } catch (error) {
            console.error("Upload failed", error.response?.data || error.message);
            Alert.alert("Upload Failed", error.response?.data?.message || "An error occurred");
        } finally {
            setIsFilePopupOpen(false);
            fetchData().catch((err) => console.error(err));
        }
    };


    const handleSubmit = async () => {
        try {
            await axiosInstance.post("/jobs/apply", {
                jobId: job.id,
                name: applicationForm.name,
                email: applicationForm.email,
                phone: applicationForm.phone,
                linkedin: applicationForm.linkedin,
                website: applicationForm.website,
            });
            Alert.alert("Success", "Application Submitted Successfully");
        } catch (error) {
            console.error("Application submission failed", error.response?.data || error.message);
            Alert.alert("Application Submission Failed", error.response?.data?.message || "An error occurred");
        } finally {
            navigation.navigate("Main");
        }
    };


    return (
        <>
            <View style={ styles.container }>
                <View style={ styles.section }>
                    <Text style={ styles.title }>{ job.company }: { job.title }</Text>
                </View>
                <View style={ styles.section }>
                    <Text style={ styles.sectionHead }>
                        Personal Information
                    </Text>
                    { profile && (
                        <>
                            <TextInput
                                style={ styles.input }
                                onChangeText={ (value) => handleChange("name", value) }
                                value={ profile.name }
                                placeholder="Name"
                                placeholderTextColor="#6B7280"
                            />
                            <TextInput
                                style={ styles.input }
                                onChangeText={ (value) => handleChange("email", value) }
                                value={ profile.email }
                                placeholder="Email"
                                placeholderTextColor="#6B7280"
                            />
                            <TextInput
                                style={ styles.input }
                                onChangeText={ (value) => handleChange("phone", value) }
                                value={ profile.phone }
                                placeholder="Phone Number"
                                placeholderTextColor="#6B7280"
                            />
                        </>
                    ) }
                </View>

                <View style={ styles.section }>
                    <Text style={ styles.sectionHead }>
                        Website Links
                    </Text>
                    <TextInput
                        style={ styles.input }
                        onChangeText={ (value) => handleChange("linkedin", value) }
                        value={ applicationForm.linkedin }
                        placeholder="LinkedIn"
                        placeholderTextColor="#6B7280"
                    />
                    <TextInput
                        style={ styles.input }
                        onChangeText={ (value) => handleChange("portfolio", value) }
                        value={ applicationForm.website }
                        placeholder="Personal Website"
                        placeholderTextColor="#6B7280"
                    />
                </View>

                <View style={ styles.section }>
                    <Text style={ styles.sectionHead }>
                        File Upload
                    </Text>
                    { profile.resume ? (
                        <Pressable style={ styles.fileCard } onPress={ () => handleViewFile() }>
                            <Ionicons name="document" style={ styles.fileIcon } />
                            <Text style={ styles.fileText }>
                                { profile.resume }
                            </Text>
                            <Pressable
                                onPress={ () => handleEditFile() }
                                style={ styles.fileButton }
                            >
                                <Ionicons name="ellipsis-horizontal" style={ styles.ellipsis } />
                            </Pressable>
                        </Pressable>
                    ) : (
                        <Pressable onPress={ () => handleEditFile() }>
                            <LinearGradient
                                style={ styles.button }
                                colors={ ["#7C3AED", "#6366F1"] }
                                start={{ x: 0, y: 0 }}
                                end={{ x: 1, y: 0 }}
                            >
                                <Text style={ styles.buttonText }>ADD RESUME</Text>
                            </LinearGradient>
                        </Pressable>
                    ) }
                </View>

                <View style={ styles.section }>
                    <Pressable onPress={ () => handleSubmit() }>
                        <LinearGradient
                            style={ styles.button }
                            colors={ ["#7C3AED", "#6366F1"] }
                            start={{ x: 0, y: 0 }}
                            end={{ x: 1, y: 0 }}
                        >
                            <Text style={ styles.buttonText }>SUBMIT</Text>
                        </LinearGradient>
                    </Pressable>
                </View>
            </View>

            { isFilePopupOpen && (
                <FilePopup
                    file={ profile.resume }
                    onClose={ () => setIsFilePopupOpen(false) }
                    onSubmit={ handleFileUpload }
                />
            ) }
        </>
    );
};


export default ApplyScreen;