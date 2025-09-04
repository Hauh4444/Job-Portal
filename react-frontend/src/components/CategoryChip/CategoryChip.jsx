// External Libraries
import { Text, TouchableOpacity } from "react-native";

// Styles
import styles from "./CategoryChip.styles";


const CategoryChip = ({ category, selected, onPress }) => {
    return (
        <TouchableOpacity
            onPress={ onPress }
            style={[
                styles.chip,
                selected === category && styles.chipSelected,
            ]}
        >
            <Text
                style={[
                    styles.chipText,
                    selected === category && styles.chipTextSelected,
                ]}
            >
                { category }
            </Text>
        </TouchableOpacity>
    );
};


export default CategoryChip;