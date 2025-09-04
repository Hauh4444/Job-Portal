// External Libraries
import {useCallback, useState} from "react";
import { useFocusEffect } from "@react-navigation/native";
import { Pressable, ScrollView } from "react-native";
import { SearchBar } from "react-native-elements";

// Internal Components
import CategoryChip from "@/components/CategoryChip/CategoryChip";
import JobCard from "@/components/JobCard/JobCard";

// Internal Utilities
import axiosInstance from "@/utils/axiosInstance";

// Styles
import styles from "./HomeScreen.styles";

/**
 * @typedef { Object } Job
 * @property { string } id - MongoDB ObjectId as a string
 * @property { string } title - Title of the job posting
 * @property { string } company - Company of the job listing
 * @property { string } location - Location of the job
 * @property { string } employmentType - Employment type (e.g. Full-time, Part-time)
 * @property { string } workModel - Work model (e.g. Remote, On-Site, Hybrid)
 * @property { number } salaryMin - Minimum salary offered
 * @property { number } salaryMax - Maximum salary offered
 * @property { string } descriptionShort - Short job description/details
 * @property { string } descriptionLong - Longer job description/details
 * @property { string[] } requirements - List of job requirements
 * @property { string[] } responsibilities - List of responsibilities
 * @property { Date } createdAt - Creation date of the job posting
 * @property { Date } expiresAt - Expiration date of the job posting
 * @property { string } contactEmail - Contact email for applications
 */

const HomeScreen = ({ navigation }) => {
    const [search, setSearch] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("All");
    /** @type { Job[] } */
    const [jobs, setJobs] = useState([]);
    const [filteredJobs, setFilteredJobs] = useState([]);

    const categories= ["All", "Full-time", "Part-time", "Contract", "Remote", "On-Site", "Hybrid"];


    const fetchData = async () => {
        const response = await axiosInstance.get("/jobs");
        setJobs(response.data);
        setFilteredJobs(response.data);
    }


    useFocusEffect(
        useCallback(() => {
            fetchData().catch((err) => console.error(err));
        }, [])
    );


    useFocusEffect(
        useCallback(() => {
            const query = search.trim().toLowerCase();

            const filtered = jobs.filter(job => {
                const matchesQuery = query
                    ? job.title.toLowerCase().includes(query) ||
                    job.descriptionShort.toLowerCase().includes(query)
                    : true;

                const matchesCategory = selectedCategory !== "All"
                    ? job.employmentType.includes(selectedCategory) ||
                    job.workModel.includes(selectedCategory)
                    : true;

                return matchesQuery && matchesCategory;
            });

            setFilteredJobs(filtered);
        }, [selectedCategory, search])
    );


    return (
        <ScrollView style={ styles.container }>
            <SearchBar
                lightTheme
                placeholder="Search jobs, companies..."
                onChangeText={ setSearch }
                value={ search }
                platform="default"
                onClear={() => setSearch("")}
                cancelButtonTitle="Cancel"
                containerStyle={ styles.searchBarContainer }
                inputContainerStyle={ styles.searchBarInputContainer }
                inputStyle={ styles.searchBarInput }
                placeholderTextColor="#6B7280"
            />

            <ScrollView
                horizontal
                showsHorizontalScrollIndicator={ false }
                style={ styles.categoryScroll }
            >
                { categories.map((category, index) => (
                    <CategoryChip
                        key={ index }
                        category={ category }
                        selected={ selectedCategory }
                        onPress={ () => setSelectedCategory(category) }
                    />
                )) }
            </ScrollView>

            { filteredJobs.map((job, index) => (
                <Pressable key={ index } onPress={ () => navigation.navigate("Job", { job: job })}>
                    <JobCard job={ job } />
                </Pressable>
            )) }
        </ScrollView>
    );
};


export default HomeScreen;