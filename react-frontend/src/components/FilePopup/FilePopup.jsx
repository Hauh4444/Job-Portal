// External Libraries
import { View, Text, Pressable } from "react-native";
import * as DocumentPicker from "expo-document-picker";

// External Icons
import Ionicons from "react-native-vector-icons/Ionicons";

// Styles
import styles from "./FilePopup.styles";


const FilePopup = ({ file, onClose, onSubmit }) => {
    const chooseFile = async () => {
        const result = await DocumentPicker.getDocumentAsync({
            type: "*/*",
            multiple: false,
            copyToCacheDirectory: true,
        });

        if (!result.canceled) {
            await onSubmit(result.assets[0]);
        }
    };


    return (
        <View style={ styles.page }>
            <View style={ styles.card }>
                <Pressable onPress={ onClose }>
                    <Ionicons name="close" size={12} style={ styles.icon } />
                </Pressable>

                <View style={ styles.content }>
                    <Text style={ styles.text }>
                        { file || "No File" }
                    </Text>

                    <Pressable style={ styles.button } onPress={ () => chooseFile() }>
                        <Text style={ styles.buttonText }>Choose File</Text>
                    </Pressable>
                </View>
            </View>
        </View>
    );
};


export default FilePopup;
