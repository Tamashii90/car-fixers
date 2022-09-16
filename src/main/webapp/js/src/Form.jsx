import { useState } from "react";

export function Form() {
  // This will be set on the backend, on the JSP page
  const { departments } = window;
  const [dep, setDep] = useState(Object.keys(departments)[0]);
  const [role, setRole] = useState("group_member");
  const [group, setGroup] = useState(departments[dep][0]);
  const currDep = departments[dep];
  const capitalize = str => {
    const spacedStr = str.replace("_", " ");
    const words = spacedStr.split(" ").map(word => word[0].toUpperCase() + word.slice(1));
    return words.join(" ");
  };

  return (
    <form method="POST">
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">First Name</label>
        <div className="col-7">
          <input className="form-control" type="text" maxLength="20" name="first_name" defaultValue="firstName" />
        </div>
      </div>
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">Last Name</label>
        <div className="col-7">
          <input className="form-control" type="text" maxLength="20" name="last_name" defaultValue="lastName" />
        </div>
      </div>
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">Username</label>
        <div className="col-7">
          <input className="form-control" type="text" name="username" required minLength="3" maxLength="13" />
        </div>
      </div>
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">Password</label>
        <div className="col-7">
          <input className="form-control" type="password" name="password" minLength="7" required />
        </div>
      </div>
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">Department</label>
        <div className="col-7">
          <select
            className="custom-select"
            name="department"
            required
            value={dep}
            onChange={e => setDep(e.target.value)}>
            {Object.keys(departments).map(dep => (
              <option value={dep} key={dep}>
                {capitalize(dep)}
              </option>
            ))}
          </select>
        </div>
      </div>
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">Role</label>
        <div className="col-7">
          <select className="custom-select" name="role" required value={role} onChange={e => setRole(e.target.value)}>
            <option value="group_member">Group Member</option>
            <option value="group_head">Group Head</option>
            <option value="dep_head">Department Head</option>
          </select>
        </div>
      </div>
      <div className="form-group row">
        <label className="col-sm-2 col-form-label">Group</label>
        <div className="col-7">
          <select
            className="custom-select"
            name="group_num"
            disabled={role === "dep_head"}
            required
            value={role === "dep_head" ? currDep[currDep.length - 1] : group}
            onChange={e => setGroup(e.target.value)}>
            {currDep.map(group => (
              <option value={group} key={group} hidden={group.includes("head")}>
                {group}
              </option>
            ))}
          </select>
        </div>
      </div>
      <button type="submit" className="btn btn-primary" style={{ position: "relative", left: "-15px" }}>
        Add Employee
      </button>
    </form>
  );
}
