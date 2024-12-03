import Form from "../components/utils/Form/Form";

const AddProject = () => {
    return (
        <Form>
            <h1>Add Project</h1>
            <div className="form-controls">
                <label>Title</label>
                <input type="text" name="title" />
            </div>
            <div className="form-controls">
                <label>Description</label>
                <textarea name="description" />
            </div>
            <div className="form-controls">
                <label>Manager</label>
                <select name="manager">
                    {
                        [
                            {id: 1, name: "aakash"},
                            {id: 2, name: "raghu"},
                            {id: 3, name: "gunjan"},
                            {id: 4, name: "dhruv"}
                        ]
                        .map(user => <option value={user.id}>{user.name}</option>)
                    }
                </select>
            </div>
            <div>
                Personnel
                <div className="form-controls">
                    <label>Read-Only</label>
                    <select name="personnel-read-only" multiple>
                        {
                            [
                                {id: 1, name: "brett", role: "developer"},
                                {id: 2, name: "neil", role: "tester"},
                                {id: 3, name: "elon", role: "tester"},
                                {id: 4, name: "kash", role: "developer"}
                            ]
                            .map(user => <option value={user.id}>{user.name} - {user.role}</option>)
                        }
                    </select>
                </div>
                <div className="form-controls">
                    <label>Write Access</label>
                    <select name="personnel-write-access" multiple>
                        {
                            [
                                {id: 1, name: "brett", role: "developer"},
                                {id: 2, name: "neil", role: "tester"},
                                {id: 3, name: "elon", role: "tester"},
                                {id: 4, name: "kash", role: "developer"}
                            ]
                            .map(user => <option value={user.id}>{user.name} - {user.role}</option>)
                        }
                    </select>
                </div>
            </div>
            <button>Submit</button>
        </Form>
    )
}

export default AddProject;