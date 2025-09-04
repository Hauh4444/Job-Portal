// External Libraries
import { View, Text } from "react-native";

// Styles
import styles from "./JobCard.styles";


const JobCard = ({ job }) => {
    return (
        <View style={ styles.card }>
            <Text style={ styles.title }>
                { job.title }
            </Text>
            <Text style={ styles.company }>
                { job.company } • { job.location }
            </Text>
            <Text style={ styles.meta }>
                { job.employmentType } | { job.workModel }
            </Text>
            <Text style={ styles.salary }>
                ${ job.salaryMin.toLocaleString() } - ${ job.salaryMax.toLocaleString() }
            </Text>
            <Text numberOfLines={ 3 } style={ styles.description }>
                { job.descriptionShort }
            </Text>
        </View>
    );
};


export default JobCard;
