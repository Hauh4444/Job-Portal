// External Libraries
import {useCallback, useState} from "react";
import { useFocusEffect } from "@react-navigation/native";
import { View, ActivityIndicator, Text } from "react-native";
import { WebView } from "react-native-webview";
import * as FileSystem from "expo-file-system";

// Internal Utilities
import axiosInstance from "@/utils/axiosInstance";

// Styles
import styles from "./ViewFileScreen.styles";


const ViewFileScreen = ({ navigation, route }) => {
    const [fileUri, setFileUri] = useState(null);
    const [loading, setLoading] = useState(true);


    const fetchData = async () => {
        const response = await axiosInstance.get("/user/files", {
            params: { file: route.params.file },
            responseType: "arraybuffer",
        });

        const binary = new Uint8Array(response.data).reduce(
            (data, byte) => data + String.fromCharCode(byte), ""
        );
        const base64Data = btoa(binary);
        const filePath = `${FileSystem.cacheDirectory}${route.params.file}`;
        await FileSystem.writeAsStringAsync(filePath, base64Data, {
            encoding: FileSystem.EncodingType.Base64,
        });

        setFileUri(filePath);
        setLoading(false);
    };


    useFocusEffect(
        useCallback(() => {
            fetchData().catch((err) => console.error(err));
        }, [])
    );


    return (
        <>
            { loading ? (
                <View style={ styles.loading }>
                    <ActivityIndicator size="large" />
                </View>
            ) : (
                fileUri ? (
                    <WebView
                        originWhitelist={["*"]}
                        source={{ uri: fileUri }}
                        style={ styles.file }
                        useWebKit={true}
                    />
                ) : (
                    <View style={ styles.base }>
                        <Text style={ styles.text }>Unable to load file</Text>
                    </View>
                )
            ) }
        </>
    );
};

export default ViewFileScreen;
