import api from './api';

// Function to fetch data from a specific endpoint
export async function fetchData(endpoint) {
    try {
        const response = await api.get(endpoint);
        return response.data;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
}

// Function to post data to a specific endpoint
export async function postData(endpoint, data) {
    try {
        const response = await api.post(endpoint, data);
        return response.data;
    } catch (error) {
        console.error('Error posting data:', error);
        throw error;
    }
}

// Function to delete data from a specific endpoint
export async function deleteData(endpoint) {
    try {
        const response = await api.delete(endpoint);
        return response.data;
    } catch (error) {
        console.error('Error deleting data:', error);
        throw error;
    }
}
